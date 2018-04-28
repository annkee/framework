package com.ctsig.api;


import com.ctsig.base.enums.ResultCodeEnum;
import com.ctsig.base.exception.BaseException;
import com.ctsig.base.util.ResultVoUtil;
import com.ctsig.base.vo.ResultVO;
import com.ctsig.order.domain.Order;
import com.ctsig.order.dto.OrderDTO;
import com.ctsig.order.service.OrderDetailService;
import com.ctsig.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 订单接口
 *
 * @author wangan
 * @date 2018/01/05
 */
@RestController
@RequestMapping("/idc/order")
@Slf4j
public class OrderController {

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private OrderService orderService;


    /**
     * 创建订单
     *
     * @param orderDTO 商品信息
     * @return ResultVO
     */
    @PostMapping("/new")
    @ApiOperation(value = "创建订单", notes = "注意：extraAmountType 额外费用类型 0快递费 1服务超时费 2补差价，" +
            "payType 支付方式 0月结算 1支付宝 2微信 3银联 4积分抵现 5优惠券抵扣 6线下" +
            "orderStatus 订单支付状态 0待支付 1已支付 2取消 3月结")
    public ResultVO<List<OrderDTO>> newOrder(@ApiParam(value = "商品信息，createTime如：2018-04-03 00:00:00，extraAmountType如：0，" +
            "orderStatus如：0，payType如：1") @RequestBody OrderDTO orderDTO) {

        OrderDTO resultDto = orderService.createOrder(orderDTO);
        return ResultVoUtil.success(resultDto);
    }


    /**
     * 获取订单信息
     *
     * @param orderId 订单id
     * @return ResultVO<Order>
     */
    @GetMapping("/info")
    @ApiOperation(value = "获取订单信息", notes = "无需提示")
    public ResultVO<OrderDTO> orderInfo(@ApiParam(value = "订单id，如：18010893383") @RequestParam(value = "orderId", required = false) Long orderId) {
        if (orderId == null) {
            log.error("orderInfo param error: orderId={}", orderId);
            throw new BaseException(ResultCodeEnum.ParamError);
        }
        OrderDTO order = orderService.findOrder(orderId);

        return ResultVoUtil.success(order);
    }


    /**
     * 根据条件获取订单列表
     *
     * @param status 订单状态
     * @param page   当前页 0是第一页
     * @param size   大小
     * @return ResultVO
     */
    @GetMapping("/list")
    @ApiOperation(value = "根据条件获取订单列表", notes = "注意：订单状态 0：待支付  1：已支付 2：取消 3：月结")
    public ResultVO<List<OrderDTO>> orderList(@ApiParam(value = "订单状态，如：0") @RequestParam(value = "status") String status,
                                              @ApiParam(value = "当前页，如：1") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                              @ApiParam(value = "每页大小，如：10") @RequestParam(value = "size", defaultValue = "10") Integer size) {

        if (StringUtils.isEmpty(status)) {

            log.error("orderList param error: status={}, page={}, size={}", status, page, size);
            throw new BaseException(ResultCodeEnum.ParamError);
        }

        return ResultVoUtil.success(orderService.findList(status, page, size));
    }

    /**
     * 获取订单详情列表
     *
     * @param orderId 订单id
     * @param page    当前页 0是第一页
     * @param size    大小
     * @return ResultVO
     */
    @GetMapping("/detail/list")
    @ApiOperation(value = "获取订单详情列表", notes = "无需提示")
    public ResultVO<List<OrderDTO>> orderDetailList(@ApiParam(value = "订单id，如：18010893383") @RequestParam(value = "orderId") Long orderId,
                                                    @ApiParam(value = "当前页，如：1") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @ApiParam(value = "每页大小，如：10") @RequestParam(value = "size", defaultValue = "10") Integer size) {

        if (StringUtils.isEmpty(orderId)) {

            log.error("orderDetailList param error: orderId={}, page={}, size={}", orderId, page, size);
            throw new BaseException(ResultCodeEnum.ParamError);
        }

        Page<OrderDTO> orderDTOPage = orderDetailService.findList(orderId, new PageRequest(page, size));
        return ResultVoUtil.success(orderDTOPage.getContent());
    }


    /**
     * 订单状态修改
     *
     * @param orderId 订单id
     * @param status  订单状态
     * @return ResultVO
     */
    @GetMapping("/update")
    @ApiOperation(value = "订单状态修改", notes = "注意：订单状态 0：待支付  1：已支付 2：取消 3：月结")
    public ResultVO updateStatus(@ApiParam(value = "订单id，如：18010893383") @RequestParam(value = "orderId") Long orderId,
                                 @ApiParam(value = "需要更新的订单状态，如：1") @RequestParam(value = "status") String status) {

        if (StringUtils.isEmpty(status)) {
            log.error("updateStatus param error: orderId={}, status={}", orderId, status);
            throw new BaseException(ResultCodeEnum.ParamError);
        }
        Order order = orderService.update(orderId, status);

        if (order == null) {
            return ResultVoUtil.error(ResultCodeEnum.OrderNotExist);
        } else {
            return ResultVoUtil.success();
        }

    }

}
