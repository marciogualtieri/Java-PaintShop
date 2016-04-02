#!/usr/bin/python

from __future__ import print_function
import argparse
from test_case import TestCase


def parse_input_arguments():
    p = build_input_arguments_parser()
    args = p.parse_args()
    return int(args.num_test_cases), int(args.num_colors), int(args.num_customers), int(args.max_num_pairs),\
           args.output_file

def build_input_arguments_parser():
    p = argparse.ArgumentParser(description='Generate an random paint shop input file.')
    p.add_argument('num_test_cases', action='store', help='Number of test cases to generate.')
    p.add_argument('num_colors', action='store', help='Number of colors in each test case.')
    p.add_argument('num_customers', action='store', help='Number of customers in each test case.')
    p.add_argument('max_num_pairs', action='store', help='Max number of pairs in each customer.')
    p.add_argument('--output-file', action='store', dest='output_file',
                        default='input.txt', help='Name of file to output.')
    return p

def create_test_cases(num_test_cases, num_colors, num_customers, max_num_pairs):
    test_cases = []
    for _ in range(0, num_test_cases):
        test_cases.append(TestCase(num_colors, num_customers, max_num_pairs))
    return test_cases

def print_test_cases_to_file(test_cases, output_file_name):
    f = open(output_file_name,'w')
    print(len(test_cases), file=f)
    for test_case in test_cases:
        print(test_case, file=f)

def main():
    num_test_cases, num_colors, num_customers, max_num_pairs, output_file = parse_input_arguments()
    test_cases = create_test_cases(num_test_cases, num_colors, num_customers, max_num_pairs)
    print_test_cases_to_file(test_cases, output_file)

if __name__ == '__main__':
    main()