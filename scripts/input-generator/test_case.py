#!/usr/bin/python

import unittest
from customer import Customer

class TestCase:
    customers = None
    num_colors = 0
    num_customers = 0
    max_num_pairs = 0

    def __init__(self, num_colors, num_customers, max_num_pairs):
        self.num_colors = num_colors
        self.num_customers = num_customers
        self.max_num_pairs = max_num_pairs
        self.customers = self.__create_customers()

    def __str__(self):
        rep = str(self.num_colors) + '\n'
        rep += str(len(self.customers)) + '\n'
        rep += '\n'.join([str(customer) for customer in self.customers])
        return rep

    def __create_customers(self):
        customers = []
        for _ in range(0, self.num_customers):
            customers.append(Customer(self.num_colors, self.max_num_pairs))
        return customers

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