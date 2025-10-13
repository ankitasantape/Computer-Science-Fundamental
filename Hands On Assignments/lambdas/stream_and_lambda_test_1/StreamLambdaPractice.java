package stream_and_lambda_test_1;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingDouble;

class Employee{

    int id;
    String name;
    String dept;
    double salary;

    Employee(int id, String name, String dept,  double salary){
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString(){
        return "Employee("+id+", "+name+", "+dept+", "+salary+")";
    }
}

public class StreamLambdaPractice {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee(1, "Alice", "IT", 70000),
                new Employee(2, "Bob", "HR", 40000),
                new Employee(3, "Charlie", "IT", 90000),
                new Employee(4, "Diana", "HR", 60000)
        );

        System.out.println("1. Highest Salary by Dept: " + highestSalaryByDepartment(employees));

        System.out.println("2. Top 2 Frequent Words: " + topKFrequestWords(Arrays.asList("apple","banana","apple","orange","banana","apple"),2));

        System.out.println("3. Is Anagram(listen, silent):" + isAnagram("listen","silent"));

        System.out.println("4. Flatten Nested List: " +
                flattenNestedList(Arrays.asList(1,2), Arrays.asList(3,4,5),
                        Arrays.asList(6)));

        System.out.println("5. Duplicates: " + findDuplicates(Arrays.asList(1,2,3,4,2,5,6,3,7)));

        System.out.println("6. Longest Unique String: " + longestUniqueString(Arrays.asList("apple","banana","xyz","algorithm")));

        System.out.println("7. Concatenate: " + concatenateWithDelimiter(Arrays.asList("Java", "Streams", "Lambda"), "-"));

        System.out.println("8. Median: " + findMedian(Arrays.asList(7, 1, 3, 9, 5)));

        System.out.println("9. Partition Even/Odd: " + partitionEvenOdd(Arrays.asList(1, 2, 3, 4, 5, 6)));

        System.out.println("10. First Non-Repeating Char from swiss: " + firstNonRepeatingChar("swiss"));
    }

    private static Character firstNonRepeatingChar(String str) {
        Map<Character, Long> freqMap = str.chars()
                .mapToObj(c -> (char)c)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));

        return freqMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

    }

    private static Map<Boolean, List<Integer>> partitionEvenOdd(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));

    }

    private static double findMedian(List<Integer> numbers) {
        List<Integer> sorted = numbers.stream()
                .sorted()
                .collect(Collectors.toList());

        int size = sorted.size();
        if(size % 2 == 1){
            return sorted.get(size / 2);
        }
        else {
            return (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0;
        }
    }

    private static String concatenateWithDelimiter(List<String> words, String delimiter) {
        return String.join(delimiter, words);
    }

    private static String longestUniqueString(List<String> list) {
        return list.stream().filter(StreamLambdaPractice::hasAllUniqueChars) // keep only words with unique chars
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

    private static boolean hasAllUniqueChars(String str){
        Set<Character> seen = new HashSet<>();
        for (char ch : str.toCharArray()){
            if(!seen.add(ch)){
                return false;
            }
        }
        return true;
    }

    private static List<Integer> findDuplicates(List<Integer> list) {

        return list.stream().distinct().toList();
    }

    private static List<Integer> flattenNestedList(List<Integer> list, List<Integer> list1, List<Integer> list2) {
         List<List<Integer>> lists =
                 Arrays.asList(list, list1, list2);

        List<Integer> ans = lists.stream().flatMap(List::stream).collect(Collectors.toList());
        return ans;
    }

    private static boolean isAnagram(String str1, String str2) {
        if(str1.length() != str2.length()){
            return false;
        }
        Map<Integer, Long> freq1 = str1.chars()
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<Integer, Long> freq2 = str2.chars()
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return freq1.equals(freq2);

    }

    private static List<String> topKFrequestWords(List<String> words, int k) {
       Map<String, Integer> freqMap = new HashMap<>();
       for (String word : words){
           freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
       }

       return freqMap.entrySet()
               .stream()
               .sorted((e1, e2) -> e2.getValue() - e1.getValue())
               .limit(k)
               .map(Map.Entry::getKey)
               .collect(Collectors.toList());
    }

    private static Map<String, Double> highestSalaryByDepartment(List<Employee> employees) {
        return employees.stream().collect(Collectors.groupingBy(Employee::getDept,
                Collectors.collectingAndThen(
                        Collectors.maxBy(comparingDouble(Employee::getSalary)), Optional::get
                )
        )).entrySet().stream().collect(Collectors.toMap(entry->entry.getKey(),entry->entry.getValue().getSalary()));
    }
}
