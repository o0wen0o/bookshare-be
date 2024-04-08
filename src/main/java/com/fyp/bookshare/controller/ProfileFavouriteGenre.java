package com.fyp.bookshare.controller;

import com.fyp.bookshare.entity.RestBean;
import com.fyp.bookshare.entity.dto.FavouriteGenreDTO;
import com.fyp.bookshare.pojo.Genres;
import com.fyp.bookshare.pojo.UserPivotGenres;
import com.fyp.bookshare.service.admin.IGenresService;
import com.fyp.bookshare.service.admin.IUserPivotGenresService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * @author o0wen0o
 * @create 2024-04-08 9:01 PM
 */
@RestController
@RequestMapping("/api/profile-favourite-genre")
public class ProfileFavouriteGenre {

    @Resource
    IGenresService genresService;

    @Resource
    IUserPivotGenresService userPivotGenresService;

    @GetMapping("/getAllGenres")
    @Operation(summary = "Get all genres")
    public RestBean<List<Genres>> getAllGenres() {
        List<Genres> genres = genresService.list();
        return RestBean.success(genres);
    }

    @GetMapping("/getFavouriteGenres")
    @Operation(summary = "Get a list of favourite genres")
    public RestBean<List<UserPivotGenres>> getFavouriteGenres(@RequestParam Map<String, String> params) {
        Integer userId = Integer.valueOf(params.getOrDefault("userId", null));
        List<UserPivotGenres> genres = userPivotGenresService.getFavouriteGenres(userId);
        return RestBean.success(genres);
    }

    @PostMapping("/saveFavouriteGenres")
    @Operation(summary = "Save favourite genres")
    public RestBean<Void> saveFavouriteGenres(@RequestBody FavouriteGenreDTO favouriteGenreDTO) {
        List<Integer> ids = favouriteGenreDTO.getIds();
        Integer userId = favouriteGenreDTO.getUserId();
        return messageHandle(() -> userPivotGenresService.saveFavouriteGenres(ids, userId), "Failed to save favourite genres");
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
