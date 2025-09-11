package victor.training.java.records.intro;

import lombok.Value;

import java.util.List;

// shallow immutable
public record Immutable(
    String name,
    Other other,
    List<Integer> list) {
}