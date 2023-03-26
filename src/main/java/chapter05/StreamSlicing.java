package chapter05;

import chapter04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamSlicing {
    public static void main(String[] args) {
        List<Dish> specialMenu = Arrays.asList(
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                // takewhile과 dropwhile 확인용.
                new Dish("fake", true, 100, Dish.Type.OTHER)
        );

        // filter 순차적으로 모든 값들을 보고 320 이하인것들을 다 찾아낸다.
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        System.out.println(filteredMenu);

        // takewhile - 무한스트림에서도 동작한다. < 320 인 값들일경우 가져오고 아닐경우 중지한다.
        List<Dish> slicedMenu1 = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        System.out.println(slicedMenu1);

        // dropwhile
        // 프레디케이트가 처음으로 거짓이 되는 지점까지 발견된 요소를 버린다.
        // 프레디케이트가 거짓이 되면 그 지점에서 작업을 중단하고 남은 모든 요소를 반환한다.
        List<Dish> slicedMenu2 = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        System.out.println(slicedMenu2);

        // 퀴즈 5-1
        // 스트림을 잉요하여 처음 등장하는 두 고기 요리를 필터링하시오.
        List<Dish> twoMeat = specialMenu.stream()
                .filter(dish -> dish.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(Collectors.toList());

        System.out.println(twoMeat);

        // 5-3 매핑
        List<Integer> dishNameLength = specialMenu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());

        System.out.println(dishNameLength);

        //flat Map
        List<String> words = Arrays.asList("Hello", "World");
        List<String> collect = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(collect);

        //퀴즈 5-2-1
        //숫자리스트 -> 제곱근으로 반환
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());

        System.out.println(squares);

        //퀴즈 5-2-2
        //두개의 숫자리스트가 주어지면 모든 숫자 쌍의 리스트를 반환.
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);

        List<int[]> pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j})
                )
                .collect(Collectors.toList());

        for (int[] pair : pairs) {
            System.out.println(Arrays.toString(pair));
        }

        //퀴즈5-2-3
        //퀴즈5-2-2에서 합이 3으로 나누어 떨어지는 쌍만 반환.
        List<int[]> pairs2 = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .collect(Collectors.toList());
        System.out.println("paris2 --------------------");
        for (int[] pair : pairs2) {
            System.out.println(Arrays.toString(pair));
        }
    }
}
