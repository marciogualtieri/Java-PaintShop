#!/usr/bin/python

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
