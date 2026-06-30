package vn.BlogWeb.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.BlogWeb.helper.ApiResponse;
import vn.BlogWeb.model.Tag;
import vn.BlogWeb.service.TagService;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/tags")
    public ResponseEntity<ApiResponse<Tag>> postTag(@Valid @RequestBody Tag inputTag) {
        Tag outputTag = this.tagService.createTag(inputTag);

        return ApiResponse.created(outputTag);
    }

    @GetMapping("/tags")
    public ResponseEntity<ApiResponse<List<Tag>>> getTags() {
        List<Tag> tags = this.tagService.getListTags();

        return ApiResponse.success(tags);
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<ApiResponse<Tag>> getTag(@PathVariable long id) {
        Tag tag = this.tagService.getTagById(id);

        return ApiResponse.success(tag);
    }

    // API cập nhật Tag theo id
    @PutMapping("/tags/{id}")
    public ResponseEntity<ApiResponse<Tag>> putTag(@PathVariable long id,
            @Valid @RequestBody Tag updateTag) {

        // @PathVariable: Lấy id từ URL
        // @Valid: Kiểm tra dữ liệu của updateTag
        // @RequestBody: Chuyển JSON thành object Tag

        // Gọi Service để cập nhật Tag
        Tag tag = this.tagService.updateTagById(id, updateTag);

        // Trả về HTTP 200 OK cùng dữ liệu sau khi cập nhật
        return ApiResponse.success(tag);
    }


    // API xóa Tag theo id
    @DeleteMapping("/tags/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTag(@PathVariable long id) {
        // Gọi Service để xóa Tag
        this.tagService.deleteTagById(id);

        // Trả về HTTP 200 OK
        return ApiResponse.success("Xóa Tag thành công.");
    }

}
