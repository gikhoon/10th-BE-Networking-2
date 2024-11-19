package cotato.backend.domains.post.service;

import static cotato.backend.common.exception.ErrorCode.*;

import cotato.backend.api.dto.resonse.PostInfoResponse;
import cotato.backend.domains.post.PostRepository;
import cotato.backend.domains.post.entity.Post;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cotato.backend.common.excel.ExcelUtils;
import cotato.backend.common.exception.ApiException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final AsyncService asyncService;

    // 로컬 파일 경로로부터 엑셀 파일을 읽어 Post 엔터티로 변환하고 저장
    @Transactional
    public void saveEstatesByExcel(String filePath) {
        try {
            // 엑셀 파일을 읽어 데이터 프레임 형태로 변환
            List<Post> posts = ExcelUtils.parseExcelFile(filePath).stream()
                    .map(row -> {
                        String title = row.get("title");
                        String content = row.get("content");
                        String name = row.get("name");

                        return new Post(title, content, name);
                    })
                    .toList();

            postRepository.saveAll(posts);
        } catch (Exception e) {
            log.error("Failed to save estates by excel", e);
            throw ApiException.from(INTERNAL_SERVER_ERROR);
        }
    }

    public PostInfoResponse findPostById(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> ApiException.from(POST_NOT_FOUND));
        asyncService.increaseViews(post);
        return PostInfoResponse.from(post);
    }

    @Transactional
    public void savePost(String title, String content, String name) {
        postRepository.save(new Post(title, content, name));
    }
}
