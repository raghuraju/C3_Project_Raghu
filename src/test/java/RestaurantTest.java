import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    static Restaurant restaurant;
    static Restaurant restaurantSpy;
    static int menuSize;
    List<String> items;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeAll
    public static void setUp(){
        restaurant = new Restaurant("Some Cafe", "Delhi", LocalTime.parse("10:00"), LocalTime.parse("12:00"));
        restaurantSpy = Mockito.spy(restaurant);

        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @BeforeEach
    public void size() {
        menuSize = restaurant.getMenu().size();
        items = new ArrayList<>();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        when(restaurantSpy.getCurrentTime()).thenReturn(LocalTime.of(11,0));
        assertTrue(restaurantSpy.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        when(restaurantSpy.getCurrentTime()).thenReturn(LocalTime.of(9, 0));
        assertFalse(restaurantSpy.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(menuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(menuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void should_return_zero_for_empty_order() {
        int total = restaurant.getOrderTotal(items);
        assertEquals(0, total);
    }

    @Test
    public void should_return_correct_total_for_non_empty_order() {
        items.add("Sweet corn soup");
        items.add("Vegetable lasagne");

        int total = restaurant.getOrderTotal(items);
        assertEquals(388, total);
    }
}