package com.fyp.bookshare.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.pojo.Genres;
import com.fyp.bookshare.service.admin.IGenresService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@RestController
@RequestMapping("/api/genres")
public class GenresController {
    
    @Resource
    IGenresService genresService;

    @GetMapping("/")
    @Operation(summary = "Get a list of genres")
    public RestBean<IPage<Genres>> getGenres(@RequestParam Map<String, String> params) {
        long current = Long.parseLong(params.getOrDefault("current", "1"));
        long size = Long.parseLong(params.getOrDefault("size", "5"));
        String filter = params.getOrDefault("filter", "");

        Page<Genres> page = new Page<>(current, size);
        IPage<Genres> genres = genresService.getGenres(page, filter);
        return RestBean.success(genres);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a genre by its ID")
    public RestBean<Genres> getGenreById(@PathVariable Long id) {
        Genres genre = genresService.getById(id);
        if (genre != null) {
            return RestBean.success(genre);
        } else {
            return RestBean.failure(400, "Genre not found");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Add a new genre")
    public RestBean<Void> addGenre(@RequestBody Genres genre) {
        return messageHandle(() -> genresService.save(genre), "Failed to add the genre");
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Update an existing genre")
    public RestBean<Void> updateGenre(@PathVariable Integer id, @RequestBody Genres genre) {
        genre.setId(id);
        return messageHandle(() -> genresService.updateById(genre), "Failed to update the genre");
    }

    @DeleteMapping("/delete/{ids}")
    @Operation(summary = "Delete a genre")
    public RestBean<Void> deleteGenres(@PathVariable List<Integer> ids) {
        return messageHandle(() -> genresService.removeByIds(ids), "Failed to delete the genre");
    }

    /**
     * 针对于返回值为String作为错误信息的方法进行统一处理
     *
     * @param action 具体操作
     * @return 响应结果
     */
    private RestBean<Void> messageHandle(BooleanSupplier action, String failureMessage) {
        boolean result = action.getAsBoolean();
        if (result) {
            return RestBean.success();
        } else {
            return RestBean.failure(400, failureMessage);
        }
    }

}
