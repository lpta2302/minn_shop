package dev.minn_shop.minn_shop.product.tag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.minn_shop.minn_shop.product.DetailProductRecord;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> saveNewTags(DetailProductRecord productRecord) {
        Map<String, Tag> tags = new HashMap<>();
        Set<Tag> unSavedTags = new HashSet<>();

        tagRepository.findByNameIn(productRecord.tags())
                .stream().forEach((tag) -> {
                    tags.put(tag.getName(), tag);
                });

        productRecord.tags().stream()
                .forEach(tagName -> {
                    if (!tags.containsKey(tagName)) {
                        unSavedTags.add(
                                Tag.builder().name(tagName).build());
                    }
                });

        List<Tag> savedTags = tagRepository.saveAll(unSavedTags.stream().collect(Collectors.toList()));
        savedTags.addAll(tags.values());

        return savedTags;
    }
}
