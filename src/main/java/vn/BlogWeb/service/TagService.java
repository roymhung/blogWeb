package vn.BlogWeb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BlogWeb.helper.exception.ResourceAlreadyExistsException;
import vn.BlogWeb.helper.exception.ResourceNotFoundException;
import vn.BlogWeb.model.Tag;
import vn.BlogWeb.repository.TagRepository;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag createTag(Tag tag) {
        // Kiểm tra xem Tag có cùng tên đã tồn tại trong Database chưa
        if (this.tagRepository.existsByName(tag.getName())) {
            // Nếu đã tồn tại thì ném Exception
            // Exception này sẽ được GlobalExceptionHandler xử lý
            throw new ResourceAlreadyExistsException(
                    "Tag với name = " + tag.getName() + " đã tồn tại.");
        }

        return this.tagRepository.save(tag);
    }

    public List<Tag> getListTags() {
        return this.tagRepository.findAll();
    }


    public Tag getTagById(long id) {
        // Tìm Tag theo id
        // Nếu không tìm thấy thì ném ResourceNotFoundException
        return this.tagRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Không tồn tại Tag với id = " + id));
    }


    public Tag updateTagById(long id, Tag updateTag) {
        // Kiểm tra tên Tag mới đã tồn tại chưa
        if (this.tagRepository.existsByName(updateTag.getName())) {
            throw new ResourceAlreadyExistsException(
                    "Tag với name = " + updateTag.getName() + " đã tồn tại.");
        }

        Tag tagInDB = this.tagRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Không tồn tại Tag với id = " + id));

        tagInDB.setName(updateTag.getName());

        return this.tagRepository.save(tagInDB);
    }

    public void deleteTagById(long id) {
        this.tagRepository.deleteById(id);
    }

}
