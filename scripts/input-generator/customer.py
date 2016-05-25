#!/usr/bin/python

import random

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

    @staticmethod
    def __append_pairs(pairs, pairs_colors, finish):
        for color in pairs_colors:
            pairs.append((color, finish))

    @staticmethod
    def __sort_pairs_by_color(pairs):
        return sorted(pairs, key=lambda t: t[0])

    @staticmethod
    def __create_pairs_colors(num_pairs, num_colors):
        return random.sample(range(1, num_colors + 1), num_pairs)
