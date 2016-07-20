package com.thoughtworks.books.controller.ShoppingCartController;

import com.thoughtworks.books.controller.ShopCartController;
import com.thoughtworks.books.entity.Book;
import com.thoughtworks.books.service.BookService;
import com.thoughtworks.books.service.ShoppingCartService;
import com.thoughtworks.books.service.impl.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CartListControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService = new BookServiceImpl();

    @Mock
    private ShoppingCartService shoppingCart;

    @InjectMocks
    private ShopCartController controller;

    List<Book> bookList = new ArrayList<>();
    Book first = new Book("Java", "1-5555-t166-0", "Java Book", new BigDecimal(150));

    @Before
    public void setUp() throws Exception {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        bookList.add(first);

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver).build();

        when(shoppingCart.getCartList()).thenReturn(bookList);

    }

    @Test
    public void testCartListMethodReturnVieName() throws Exception {
        mockMvc.perform(get("/shop-cart/cart-list"))
                .andExpect(view().name("cart-list"));
    }

    @Test
    public void test() throws Exception {

        mockMvc.perform(get("/shop-cart/cart-list"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCartListAttributeExist() throws Exception {
        mockMvc.perform(get("/shop-cart/cart-list"))
                .andExpect(model().attribute("cartList", shoppingCart.getCartList()));

    }

    @Test
    public void testCartSizeAttributeExist() throws Exception {
        mockMvc.perform(get("/shop-cart/cart-list"))
                .andExpect(model().attribute("cartSize", shoppingCart.getShopCartCount()));

    }
    @Test
    public void testCartTotalAttributeExist() throws Exception {
        mockMvc.perform(get("/shop-cart/cart-list"))
                .andExpect(model().attribute("cartTotal", shoppingCart.getCartTotal()));

    }
}