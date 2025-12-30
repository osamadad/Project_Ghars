package com.tuwaiq.project_ghars.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth

                        /* ================= PUBLIC (permitAll) ================= */

                        // Register
                        .requestMatchers(
                                "/api/v1/customer/register",
                                "/api/v1/farmer/register",
                                "/api/v1/driver/register",
                                "/api/v1/user/register"
                        ).permitAll()

                        // Learn planting (Public)
                        .requestMatchers(
                                "/api/v1/plant/learn/green-house",
                                "/api/v1/plant/identify/{organ}",
                                "/api/v1/plant/identify-diseases/{organ}",
                                "/api/v1/plant/learn/water-planting"
                        ).permitAll()

                        // Event (view) Public
                        .requestMatchers(
                                "/api/v1/event/get",
                                "/api/v1/event/get/{eventId}",
                                "/api/v1/event/upcoming",
                                "/api/v1/event/upcoming/city/{city}",
                                "/api/v1/farmer/most-seasonal-yield",
                                "/api/v1/farmer/reset-seasonal-yield",
                                "/api/v1/virtual-plot/decrease-sun/{plotId}",
                                "/api/v1/virtual-plot/decrease-water/{plotId}",
                                "/api/v1/virtual-plot/check-plant/{plotId}"
                        ).permitAll()

                        // Plant (view & filter) Public
                        .requestMatchers(
                                "/api/v1/plant/get",
                                "/api/v1/plant/family/{family}",
                                "/api/v1/plant/category/{category}",
                                "/api/v1/plant/size/{size}",
                                "/api/v1/plant/growth-speed/{growthSpeed}",
                                "/api/v1/plant/water-needs/{waterNeeds}",
                                "/api/v1/plant/sun-needs/{sunNeeds}",
                                "/api/v1/plant/season/{season}",
                                "/api/v1/plant/difficulty/{difficultyLevel}",
                                "/api/v1/plant/growing-medium/{growingMedium}",
                                "/api/v1/plant/planting-place/{plantingPlace}",
                                "/api/v1/plant/life-span/{lifeSpan}"
                        ).permitAll()

                        // Product (view) Public
                        .requestMatchers(
                                "/api/v1/product/get-all",
                                "/api/v1/product/by-sell-type/{sellType}",
                                "/api/v1/product/order-by-price",
                                "/api/v1/product/price-range/{minPrice}/{maxPrice}"
                        ).permitAll()

                        // Review (view) Public
                        .requestMatchers(
                                "/api/v1/review/get",
                                "/api/v1/review/get-by-farm/{farmId}"
                        ).permitAll()

                        /* ================= ADMIN ================= */
                        .requestMatchers(
                                "/api/v1/achievement/add",
                                "/api/v1/achievement/get",
                                "/api/v1/achievement/update/{achievementId}",
                                "/api/v1/achievement/delete/{achievementId}",

                                "/api/v1/level/add",
                                "/api/v1/level/get",
                                "/api/v1/level/update/{levelId}",
                                "/api/v1/level/delete/{levelId}",

                                "/api/v1/plant/add",
                                "/api/v1/plant/update/{plantTypeId}",
                                "/api/v1/plant/delete/{plantTypeId}",

                                "/api/v1/farm/license/accept/{farmId}",
                                "/api/v1/farm/license/reject/{farmId}"
                        ).hasAuthority("ADMIN")

                        /* ================= ADDRESS (ALL ROLES) ================= */
                        .requestMatchers(
                                "/api/v1/address/add",
                                "/api/v1/address/get",
                                "/api/v1/address/get-my-address",
                                "/api/v1/address/update",
                                "/api/v1/address/delete"
                        ).hasAnyAuthority("CUSTOMER", "FARMER", "DRIVER", "ADMIN")

                        /* ================= FARMER ================= */
                        .requestMatchers(
                                // Farm
                                "/api/v1/farm/add",
                                "/api/v1/farm/get",
                                "/api/v1/farm/get-my-farm",
                                "/api/v1/farm/update/{farmId}",
                                "/api/v1/farm/delete/{farmId}",

                                // Field
                                "/api/v1/field/add",
                                "/api/v1/field/get",
                                "/api/v1/field/get-by-farm/{farmId}",
                                "/api/v1/field/update/{fieldId}",
                                "/api/v1/field/delete/{fieldId}",

                                // Yield
                                "/api/v1/yield/add",
                                "/api/v1/yield/get",
                                "/api/v1/yield/get-by-field/{fieldId}",
                                "/api/v1/yield/update/{yieldId}",
                                "/api/v1/yield/delete/{yieldId}",

                                // Stock
                                "/api/v1/stock/add",
                                "/api/v1/stock/get-all",
                                "/api/v1/stock/my-stock",
                                "/api/v1/stock/update/{stockId}",
                                "/api/v1/stock/delete/{stockId}",

                                // Product (Farmer manage)
                                "/api/v1/product/add",
                                "/api/v1/product/my-products",
                                "/api/v1/product/update/{productId}",
                                "/api/v1/product/delete/{productId}",

                                // Farmer
                                "/api/v1/farmer/get",
                                "/api/v1/farmer/get-my-info",
                                "/api/v1/farmer/update",
                                "/api/v1/farmer/delete",
                                "/api/v1/farmer/by-city/{city}",
                                "/api/v1/farmer/by-rank/{rank}",
                                "/api/v1/farmer/by-level/{minLevel}/{maxLevel}",
                                "/api/v1/farmer/most-level",
                                "/api/v1/farmer/planted/{plantName}",
                                "/api/v1/farmer/most-yield",
                                "/api/v1/farmer/talk/{farmerId}/{message}",
                                "/api/v1/farmer/talk-about-plant/{farmerId}/{plantName}",

                                // Farmer Achievement
                                "/api/v1/farmer-achievement/unlock/{achievementId}",
                                "/api/v1/farmer-achievement/my",

                                // Virtual Farm
                                "/api/v1/virtualfarm/add",
                                "/api/v1/virtualfarm/get",
                                "/api/v1/virtualfarm/get/{virtualFarmId}",
                                "/api/v1/virtualfarm/update/{virtualFarmId}",
                                "/api/v1/virtualfarm/delete/{virtualFarmId}",
                                "/api/v1/virtual-plot/add-water/{plotId}",
                                "/api/v1/virtual-plot/add-sun/{plotId}",
                                "/api/v1/virtual-plot/harvest/{plotId}",

                                // Virtual Plot
                                "/api/v1/virtual-plot/add/{virtualFarmId}/{plotType}",
                                "/api/v1/virtual-plot/get/{virtualFarmId}",
                                "/api/v1/virtual-plot/get-by-id/{virtualPlotId}",
                                "/api/v1/virtual-plot/delete/{virtualPlotId}",
                                "/api/v1/virtual-plot/assign-plant/{plotId}/{plantId}",
                                "/api/v1/virtual-plot/uproot/{plotId}",

                                // AI
                                "/api/v1/ai/soil-seeds",
                                "/api/v1/ai/home-gardening",
                                "/api/v1/ai/watering-fertilizing",
                                "/api/v1/ai/plant-care",
                                "/api/v1/ai/plant-problems",
                                "/api/v1/ai/recommend-event",
                                "/api/v1/ai/ai/season-plants/{season}",
                                "/api/v1/ai/ai/smart-irrigation",
                                "/api/v1/ai/ai/recommend-plant",
                                "/api/v1/ai/ai/filter-plants-by-location/{city}",
                                "/api/v1/ai/{plantId}",
                                "/api/v1/ai/add/{plantName}",

                                // Delivery create/update (if you فعلاً تبيها للمزارع)
                                "/api/v1/delivery/create/{orderId}/{driverId}",
                                "/api/v1/delivery/update-status/{deliveryId}"
                        ).hasAuthority("FARMER")

                        /* ================= CUSTOMER ================= */
                        .requestMatchers(
                                // Customer
                                "/api/v1/customer/get",
                                "/api/v1/customer/update",
                                "/api/v1/customer/delete",

                                // Order
                                "/api/v1/order/get-all",
                                "/api/v1/order/my-orders",
                                "/api/v1/order/create",
                                "/api/v1/order/pay/{orderId}",
                                "/api/v1/order/return/{orderId}",
                                "/api/v1/order/confirm-return/{orderId}",
                                "/api/v1/order/update-status/{orderId}",
                                "/api/v1/order/delete/{orderId}",

                                // Order Item
                                "/api/v1/order-item/get/{orderId}",
                                "/api/v1/order-item/add/{orderId}",
                                "/api/v1/order-item/update/{orderItemId}",
                                "/api/v1/order-item/delete/{orderItemId}",

                                // Payment & Invoice
                                "/api/v1/payment/pay/{orderId}",
                                "/api/v1/invoice/get-all",
                                "/api/v1/invoice/my-invoices",
                                "/api/v1/invoice/create/{orderId}",
                                "/api/v1/invoice/update/{invoiceId}",
                                "/api/v1/invoice/delete/{invoiceId}",

                                // Review (write)
                                "/api/v1/review/add/{farmId}",
                                "/api/v1/review/update/{reviewId}",
                                "/api/v1/review/delete/{reviewId}",

                                // Event interaction
                                "/api/v1/event/join/{eventId}",
                                "/api/v1/event/leave/{eventId}"
                        ).hasAuthority("CUSTOMER")

                        /* ================= DRIVER ================= */
                        .requestMatchers(
                                "/api/v1/driver/get",
                                "/api/v1/driver/update",
                                "/api/v1/driver/delete",
                                "/api/v1/delivery/my",
                                "/api/v1/delivery/update-status/{deliveryId}"
                        ).hasAuthority("DRIVER")

                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .httpBasic(basic -> { })
                .build();
    }
}
