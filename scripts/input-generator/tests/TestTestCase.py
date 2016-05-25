#!/usr/bin/python

import unittest

from test_case import TestCase


class TestCaseTests(unittest.TestCase):
    NUM_COLORS = 10
    NUM_CUSTOMERS = 3
    MAX_NUM_PAIRS = 5

    TEST_CASE = TestCase(NUM_COLORS, NUM_CUSTOMERS, MAX_NUM_PAIRS)

    def test__when_test_case_is_created__then_num_customers_ok(self):
        self.assertEquals(len(self.TEST_CASE.customers), self.NUM_CUSTOMERS)

    def test__when_test_case_is_created__then_num_colors_ok(self):
        self.assertEquals(self.TEST_CASE.num_colors, self.NUM_COLORS)


def main():
    unittest.main()


if __name__ == '__main__':
    main()
