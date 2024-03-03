package com.fyp.bookshare.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author o0wen0o
 * @since 2024-02-28
 */
@Getter
@Setter
@TableName("fundraising_projects")
@ApiModel(value = "FundraisingProjects对象", description = "")
public class FundraisingProjects implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("project_name")
    private String projectName;

    @TableField("description")
    private String description;

    @TableField("start_date")
    private LocalDate startDate;

    @TableField("end_date")
    private LocalDate endDate;

    @TableField("goal_amount")
    private BigDecimal goalAmount;

    @TableField("current_amount")
    private BigDecimal currentAmount;

    @TableField("status")
    private String status;

    @TableField("organizer_id")
    private Integer organizerId;
}
