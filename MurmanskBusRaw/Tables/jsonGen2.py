# -*- coding: utf-8 -*-
"""
Created on Tue Sep 16 08:27:01 2014

@author: pavel
"""
import csv, sys
import json
from itertools import izip

delimiter = ';'
asterisk ='*'

import StringIO

#

def get_csv_reader(f):
    return  csv.reader(f, delimiter=';')


def transpose(reader):
    return izip(*reader)
    
def split(table):
    flag = True
    one =[]
    another =[]
    for line in table:
        for entry in line:
            if len(entry) != 0:
                if flag:
                    one.append(entry)
                else:
                    another.append(entry)
            else:
                flag = False
    return one, another
    
def create_offset_list(lisT, extra_val, def_val='0' ):
    l = []
    for elment in lisT:
        if elment[-1]=='*':
            l.append(extra_val)
        else:
            l.append(def_val)
        
    return l

def standardiz_list(l):
    for i in range(len(l)):
        l[i] = l[i].replace(':', '.')
        l[i] = l[i].replace('*', '')
    return l
def list_2_string(lisT):
    separator = '", "'
    s = separator.join(lisT)
    return '"' + s + '"'

def write(strings, filename):
    f = open(filename, 'w')
    for s in strings:
        f.write(s+'\n')
    f.close()

def main(filename, f):
    reader = get_csv_reader(f)
    a = transpose(reader)
    one, another = split(a)
    z_one, z_another = create_offset_list(one, '2'), create_offset_list(another, '2')
    standardiz_list(one)
    standardiz_list(another)
    
    s = map(list_2_string, [one, z_one, another,  z_another])
    write(s, filename)
    f.close()

if __name__ == '__main__':
    mode = sys.argv[1]
    if mode == 'f':
        for filename in sys.argv[2:]:
            f=open(filename, 'r')
            main(filename+'_json.txt', f)
            f.close()
    elif mode == 's':
        filename = sys.argv[2]
        f = StringIO.StringIO(sys.argv[3])
        main(filename, f)
    
    
