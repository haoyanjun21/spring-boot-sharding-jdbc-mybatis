/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.example.spring.boot.mybatis.service;

import io.shardingsphere.example.spring.boot.mybatis.entity.Order;
import io.shardingsphere.example.spring.boot.mybatis.entity.OrderItem;
import io.shardingsphere.example.spring.boot.mybatis.repository.OrderItemRepository;
import io.shardingsphere.example.spring.boot.mybatis.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DemoService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private OrderItemRepository orderItemRepository;

    public void demo() {
        orderRepository.createIfNotExistsTable();
        orderItemRepository.createIfNotExistsTable();
        orderRepository.truncateTable();
        orderItemRepository.truncateTable();
        List<Long> orderIds = new ArrayList<>(10);
        System.out.println("1.Insert--------------");
        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            int userId = ThreadLocalRandom.current().nextInt(100);
            order.setUserId(userId);
            order.setStatus(ThreadLocalRandom.current().nextInt(100));
            orderRepository.insert(order);
            long orderId = order.getOrderId();
            orderIds.add(orderId);

            OrderItem item = new OrderItem();
            item.setOrderId(orderId);
            item.setUserId(userId);
            orderItemRepository.insert(item);
        }
        long start = System.currentTimeMillis();
        System.out.println(orderRepository.selectAll());
        System.out.println("selectAll() 耗时 " + (System.currentTimeMillis() - start) +" 秒");
        System.out.println("总数据量 : " + orderRepository.count());
//        System.out.println("2.Delete--------------");
//        for (Long each : orderIds) {
//            orderRepository.delete(each);
//            orderItemRepository.delete(each);
//        }
//        System.out.println(orderItemRepository.selectAll());
//        orderItemRepository.dropTable();
//        orderRepository.dropTable();
    }
}
