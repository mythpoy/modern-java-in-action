package chapter04;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class M {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );

        List<String> threeHighCaloricDishNames = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName) // 메소드참조 , lambda로는 (d -> d.getName())
                .limit(3) // 고칼로리 요리 3개를 잧으면 중지가 되기때문에 최적화가 가능하다.
                .collect(Collectors.toList()); // stream to list

        System.out.println(threeHighCaloricDishNames);

        List<String> title = Arrays.asList("Java", "In", "Action");
        Stream<String> s = title.stream();
        s.forEach(System.out::println);
        //s.forEach(System.out::println); // Stream은 단 한번만 소비 할 수 있다.


        //퀴즈 4-1
        List<String> highCaloricDishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .collect(Collectors.toList());
        System.out.println(highCaloricDishes);

        //4-4 스트림연산
        //4-4-1 중간연산
        //filter와 map은 서로 다른연산이지만 한 과정으로 병합된다.
        //이 기법을 loop fusion 이라고 한다.
        List<String> names =
                menu.stream()
                        .filter(dish -> {
                            System.out.println("filtering: " + dish.getName());
                            return dish.getCalories() > 300;
                        })
                        .map(dish -> {
                            System.out.println("mapping: " + dish.getName());
                            return dish.getName();
                        })
                        .limit(3)
                        .collect(Collectors.toList());
        System.out.println(names);

    }
}
