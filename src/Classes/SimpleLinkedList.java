package Classes;

import java.util.Iterator;

import static java.lang.Math.abs;
/*
Требуется решить задачу в 2-х вариантах:
1. С использованием стека / очереди, самостоятельно реализованных на основе
связного списка.
2. С использованием реализации стека / очереди, которая уже есть в стандартной
библиотеки языка Java.
Решение задачи предполагает наличие оконного интерфейса для демонстрации работы
программы. Должен быть предусмотрен ввод исходных данных из файла.
Если в условии задачи что-то непонятно – попросить пояснить преподавателя.


Смоделировать игру ход карточной игры «Пьяница»:
https://ru.wikipedia.org/wiki/Пьяница_(карточная_игра). 36 перемешиваются и раздаются
2-м игрокам поровну. Карты каждого игрока представлены в виде очереди. Каждый ход
оба игрока берут по карте из очереди сравнивают и тот, у кого карта старше, добавляет
обе карты в конец очереди (остальные правила, как минимум «Спор», надо также
смоделировать).
«Рисовать» анимацию не требуется, но в каком-то виде требуется показывать карты
одного и другого игрока (как вариант, в строке через запятую) и по кнопке осуществлять
ход
 */

public class SimpleLinkedList<T> implements Iterable<T> , SimpleQueue<T> {

    public static class SimpleLinkedListException extends Exception {
        public SimpleLinkedListException(String message) {
            super(message);
        }
    }

    private class SimpleLinkedListNode<T> {
        public T value;
        public SimpleLinkedListNode<T> next;

        public SimpleLinkedListNode (T value, SimpleLinkedListNode<T> next) {
            this.value = value;
            this.next = next;
        }

        public SimpleLinkedListNode(T value) {
            this(value, null);
        }
    }

    private SimpleLinkedListNode<T> head = null;
    private SimpleLinkedListNode<T> tail = null;
    private int count = 0;


    public void addFirst(T value) {
        head = new SimpleLinkedListNode<>(value, head);
        if (count == 0) {
            tail = head;
        }
        count++;
    }

    public void addLast(T value) {
        SimpleLinkedListNode<T> temp = new SimpleLinkedListNode<>(value);
        if (count == 0) {
            head = tail = temp;
        } else {
            tail.next = temp;
            tail = temp;
        }
        count++;
    }

    private void checkEmpty() throws SimpleLinkedListException {
        if (count == 0) {
            throw new SimpleLinkedListException("Empty list");
        }
    }

    private SimpleLinkedListNode<T> getNode(int index) {//по индексу будет возвращать элемент
        int i = 0;
        for (SimpleLinkedListNode<T> curr = head; curr != null; curr = curr.next, i++) {
            if (i == index) {
                return curr;
            }
        }
        return null;
    }

    public T removeFirst() throws SimpleLinkedListException {
        checkEmpty();

        T value = head.value;
        head = head.next;
        if (count == 1) {
            tail = null;
        }
        count--;
        return value;
    }

    public T removeLast() throws SimpleLinkedListException {
        return remove(count - 1);
    }

    public T remove(int index) throws SimpleLinkedListException {
        checkEmpty();
        if (index < 0 || index >= count) {
            throw new SimpleLinkedListException("Incorrect index");
        }

        T value;
        if (index == 0) {
            value = head.value;
            head = head.next;
        } else {
            SimpleLinkedListNode<T> prev = getNode(index - 1);
            SimpleLinkedListNode<T> curr = prev.next;
            value = curr.value;
            prev.next = curr.next;
            if (index == count - 1) {
                tail = prev;
            }
        }
        count--;
        return value;
    }

    public void insert(int index, T value) throws SimpleLinkedListException {
        if (index < 0 || index > count) {
            throw new SimpleLinkedListException("Incorrect index");
        }
        if (index == 0) {
            addFirst(value);
        } else {
            SimpleLinkedListNode<T> prev = getNode(index - 1);
            prev.next = new SimpleLinkedListNode<>(value, prev.next);
            if (index == count) {
                tail = prev.next;
            }
        }
        count++;
    }

    public int count() {
        return count;
    }

    public T getFirst() throws SimpleLinkedListException {
        checkEmpty();

        return head.value;
    }

    public T getLast() throws SimpleLinkedListException {
        checkEmpty();

        return tail.value;
    }

    public T get(int index) throws SimpleLinkedListException {
        if (index < 0 || index >= count) {
            throw new SimpleLinkedListException("Incorrect index");
        }
        return getNode(index).value;
    }

    private SimpleLinkedListNode<T> rearrangingElements(int index1, int index2) { // метод перестановки эл (эл нумеруются с 0)
        //в <> Integer тк мы
        int n = count();
        if (n > 1) { // тк можно переставлять эл только если их 2 и больше
            SimpleLinkedListNode<T> prev1 = null;
            SimpleLinkedListNode<T> prev2 = null;

            SimpleLinkedListNode<T> curr1 = null;
            SimpleLinkedListNode<T> curr2 = null;

            SimpleLinkedListNode<T> next1 = null;
            SimpleLinkedListNode<T> next2 = null;

            /*
            int i = 0;
            for (SimpleLinkedListNode<Integer> currInTheMoment = head; currInTheMoment != null; currInTheMoment = currInTheMoment.next, i++) {
                if (i == index1 - 1) {
                    prev1 = currInTheMoment;
                }
                if (i == index2 - 1) {
                    prev2 = currInTheMoment;
                }
            }
            curr1 = prev1.next;
            curr2 = prev2.next;

            if (abs(index1 - index2) > 1) {
                prev1.next = prev1.next.next;
                prev2.next = prev2.next.next;

                curr1.next = prev2.next;
                curr2.next = prev1.next;

                prev1.next = curr2;
                prev2.next = curr1;
            } else { //если эл для перестановки рядом то
                if (index1 < index2){
                    if (prev1 != null) {
                        prev1.next = curr2;
                    }
                    curr2.next = curr1;
                    curr1.next = next2;
                } else {
                    if (prev2 != null) {
                        prev2.next = curr1;
                    }
                    curr1.next = curr2;
                    curr2.next = next1;
                }
            }
            */


            int i = 0;
            for (SimpleLinkedListNode<T> currInTheMoment = head; currInTheMoment != null; currInTheMoment = currInTheMoment.next, i++) {
                /* Пыталась как-то улучшить(уменьшить код, но это тоже надо доработать и не факт что в итоге строк меньше будет)
                if ((index1 == i && i == 0 )|| (index2 == i && i == 0)){
                    if (index1 == 0){
                        curr1 = currInTheMoment;
                        next1 = currInTheMoment.next;
                    }
                    if (index2 == 0){
                        curr2 = currInTheMoment;
                        next2 = currInTheMoment.next;
                    }
                } else {*/
                if (i == index1 - 1) {
                    prev1 = currInTheMoment;
                }
                if (i == index2 - 1) {
                    prev2 = currInTheMoment;
                }

                if (i == index1) {
                    curr1 = currInTheMoment;
                }
                if (i == index2) {
                    curr2 = currInTheMoment;
                }

                if (i == index1 + 1) {
                    next1 = currInTheMoment;
                }
                if (i == index2 + 1) {
                    next2 = currInTheMoment;
                }


            }

            if (abs(index1 - index2) > 1) {
                if (prev1 != null) {
                    prev1.next = curr2;
                } else {
                    head = curr2;
                }
                if (prev2 != null) { //те тут нет prev2 те prev2 = null
                    prev2.next = curr1;
                } else {
                    head = curr1;
                }// тут пошло зацикливание тк я наверно не новый эл создала а ссыль и из - за милион эл с валуе 1 у меня ошибка памяти

                // ещё зацикливание тк  prev2  == curr1 и следовательно curr1 всегда вызывает некстом себя же
                // те если заменяемые члены не рядом то прога работает
                curr1.next = next2;
                curr2.next = next1;

            } else { //если эл для перестановки рядом то
                if (index1 < index2) {
                    if (prev1 != null) {
                        prev1.next = curr2;
                    }
                    curr2.next = curr1;
                    curr1.next = next2;
                } else {
                    if (prev2 != null) {
                        prev2.next = curr1;
                    }
                    curr1.next = curr2;
                    curr2.next = next1;
                }
            }
        }


        return null;
    }


    public void getAnswer() { //метод - основная логика моего таска

    }

    public T element() throws Exception {
        if (count() == 0) {
            throw new Exception("Queue is empty");
        }
        return (T) head.value;
    }
    //    .\input.txt .\output.txt


    @Override
    public Iterator<T> iterator() {
        class SimpleLinkedListIterator implements Iterator<T> {
            SimpleLinkedListNode<T> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                T value = curr.value;
                curr = curr.next;
                return value;
            }
        }

        return (Iterator<T>) new SimpleLinkedListIterator();
    }
}
