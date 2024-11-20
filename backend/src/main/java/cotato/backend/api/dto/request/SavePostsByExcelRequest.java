package cotato.backend.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SavePostsByExcelRequest {

	@Schema(description = "엑셀 파일 경로")
	private String path;
}
