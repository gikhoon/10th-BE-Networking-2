package cotato.backend.api;

import cotato.backend.api.dto.request.SavePostRequest;
import cotato.backend.api.dto.resonse.PagedPostResponse;
import cotato.backend.api.dto.resonse.PostInfoResponse;
import cotato.backend.domains.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cotato.backend.common.dto.DataResponse;
import cotato.backend.api.dto.request.SavePostsByExcelRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/excel")
    public ResponseEntity<DataResponse<Void>> savePostsByExcel(@RequestBody SavePostsByExcelRequest request) {
        postService.saveEstatesByExcel(request.getPath());

        return ResponseEntity.ok(DataResponse.ok());
    }

    @GetMapping
    public ResponseEntity<DataResponse<PagedPostResponse>> getPosts(
            @RequestParam(defaultValue = "0") int page) {
        PagedPostResponse response = postService.findPostsByLikes(page);

        return ResponseEntity.ok(DataResponse.from(response));
    }

    @PostMapping
    public ResponseEntity<DataResponse<Void>> savePost(@Valid @RequestBody SavePostRequest request) {
        postService.savePost(request.getTitle(), request.getContent(), request.getName());

        return ResponseEntity.ok(DataResponse.ok());
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<DataResponse<PostInfoResponse>> findPostById(@PathVariable(name = "post_id") long postId) {
        PostInfoResponse postInfo = postService.findPostById(postId);

        return ResponseEntity.ok(DataResponse.from(postInfo));
    }
}
