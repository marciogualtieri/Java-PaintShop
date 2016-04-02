#!/usr/bin/python

import random
import unittest

GLOSSY = 0
MATTE = 1

class Customer:

    num_colors = 0
    max_num_pairs = 0
    pairs = None

    def __init__(self, num_colors, max_num_pairs):
        self.num_colors = num_colors
        self.max_num_pairs = min(max_num_pairs, num_colors)
        self.pairs = self.__create_pairs()

    def __str__(self):
        flatten_pairs = list(sum(self.pairs, ()))
        rep = str(len(self.pairs)) + ' '
        rep += ' '.join([str(p) for p in flatten_pairs])
        return rep

    def __create_pairs(self):
        pairs = []
        num_pairs = random.randint(1, self.max_num_pairs)
        pairs_colors = self.__create_pairs_colors(num_pairs, self.num_colors)
        self.__append_pairs(pairs, pairs_colors[1:num_pairs], GLOSSY)
        finish = random.choice([GLOSSY, MATTE])
        self.__append_pairs(pairs, pairs_colors[0:1], finish)
        pairs = self.__sort_pairs_by_color(pairs)
        return pairs

    def __append_pairs(self, pairs, pairs_colors, finish):
        for color in pairs_colors:
            pairs.append((color, finish))

    def __sort_pairs_by_color(self, pairs):
        return sorted(pairs, key=lambda t: t[0])

    def __create_pairs_colors(self, num_pairs, num_colors):
        return random.sample(range(1, num_colors + 1), num_pairs)

class CustomerTests(unittest.TestCase):

    NUM_COLORS = 10
    MAX_NUM_PAIRS = 5
    CUSTOMER = Customer(NUM_COLORS, MAX_NUM_PAIRS)

    def test__when_customer_is_created__then_num_pairs_ok(self):
        self.failUnless(1 <= len(self.CUSTOMER.pairs) <= self.MAX_NUM_PAIRS)

    def test__when_customer_is_created__then_colors_ok(self):
        pairs = self.__get_pairs_with_invalid_colors(self.CUSTOMER.pairs, self.NUM_COLORS)
        self.assertEqual(len(pairs), 0)

    def test__when_customer_is_created__then_color_finishes_ok(self):
        pairs = self.__get_pairs_with_invalid_finishes(self.CUSTOMER.pairs)
        self.assertEqual(len(pairs), 0)

    def test__when_customer_is_created__then_matte_colors_ok(self):
        pairs = self.__get_pairs_with_matte_finish(self.CUSTOMER.pairs)
        self.failUnless(len(pairs) <= 1)

    def __get_pairs_with_invalid_finishes(self, pairs):
        return filter(lambda x: x[1] != GLOSSY and x[1] != MATTE, pairs)

    def __get_pairs_with_matte_finish(self, pairs):
        return filter(lambda x: x[1] == 1, self.CUSTOMER.pairs)

    def __get_pairs_with_invalid_colors(self, pairs, num_colors):
        return filter(lambda x: x[0] < 1 or x[0] > num_colors, pairs)

def main():
    unittest.main()

if __name__ == '__main__':
    main()