package org.lavender.library.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEcharts {
    private List<String> names =  new ArrayList<>();
    private List<Integer> counts = new ArrayList<>();
}