#!/usr/bin/python

import unittest

from customer import Customer, GLOSSY, MATTE


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
        pairs = self.__get_pairs_with_matte_finish()
        self.failUnless(len(pairs) <= 1)

    @staticmethod
    def __get_pairs_with_invalid_finishes(pairs):
        return filter(lambda x: x[1] != GLOSSY and x[1] != MATTE, pairs)

    def __get_pairs_with_matte_finish(self):
        return filter(lambda x: x[1] == 1, self.CUSTOMER.pairs)

    @staticmethod
    def __get_pairs_with_invalid_colors(pairs, num_colors):
        return filter(lambda x: x[0] < 1 or x[0] > num_colors, pairs)
