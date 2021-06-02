package io.github.raeperd.realworld.application.article;

import io.github.raeperd.realworld.domain.article.ArticleService;
import io.github.raeperd.realworld.infrastructure.jwt.UserJWTPayload;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.of;

@RestController
class ArticleRestController {

    private final ArticleService articleService;

    ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles")
    public ArticleModel postArticle(@AuthenticationPrincipal UserJWTPayload jwtPayload,
                                    @Valid @RequestBody ArticlePostRequestDTO dto) {
        var articleCreated = articleService.createNewArticle(jwtPayload.getUserId(), dto.toArticleContents());
        return ArticleModel.fromArticle(articleCreated);
    }

    @GetMapping("/articles")
    public MultipleArticleModel getArticles(Pageable pageable) {
        final var articles = articleService.getArticles(pageable);
        return MultipleArticleModel.fromArticles(articles);
    }

    @GetMapping(value = "/articles", params = {"author"})
    public MultipleArticleModel getArticlesByAuthor(@RequestParam String author, Pageable pageable) {
        final var articles = articleService.getArticlesByAuthorName(author, pageable);
        return MultipleArticleModel.fromArticles(articles);
    }

    @GetMapping(value = "/articles", params = {"tag"})
    public MultipleArticleModel getArticlesByTag(@RequestParam String tag, Pageable pageable) {
        final var articles = articleService.getArticlesByTag(tag, pageable);
        return MultipleArticleModel.fromArticles(articles);
    }

    // TODO: GET /articles?favorited={username}

    @GetMapping("/articles/{slug}")
    public ResponseEntity<ArticleModel> getArticleBySlug(@PathVariable String slug) {
        return of(articleService.getArticleBySlug(slug)
                .map(ArticleModel::fromArticle));
    }

    @PostMapping("/articles/{slug}/favorite")
    public ArticleModel favoriteArticleBySlug(@AuthenticationPrincipal UserJWTPayload jwtPayload,
                                              @PathVariable String slug) {
        var articleFavorited = articleService.favoriteArticle(jwtPayload.getUserId(), slug);
        return ArticleModel.fromArticle(articleFavorited);
    }
}
