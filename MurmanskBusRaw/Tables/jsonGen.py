# -*- coding: utf-8 -*-
"""
Created on Tue Sep 16 08:27:01 2014

@author: pavel
"""
import csv, sys
import json
from itertools import izip

reload(sys)
sys.setdefaultencoding('utf-8')

delimiter = ';'
asterisk='*'
integers = map(str,range(10))

from_N = "from_north"
from_S = "from_south"
work_d = "w_days"
sun_d = "sunday"
sat_d = "saturday"
dep="departures"
off="offsets"

south_off_values={'5.txt':9,'18.txt':6}

dictionary = {from_N:{work_d:{}, sat_d:{},sun_d:{}}, from_S:{work_d:{}, sat_d:{},sun_d:{}}}
day_order = [work_d, sat_d, sun_d]

import StringIO

#

def get_csv_reader(f):
    return  csv.reader(f, delimiter=';')

def transpose(reader):
    return izip(*reader)
    
def split_table(table):
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


def offset_gen(l, extra_val=0, def_val=0):
    offs =[]
    for element in l:
        if element[-1]=='*':
            offs.append(extra_val)
        else:
            offs.append(def_val)
    return offs

def split_days(f):
    days = []
    temp =''
    flag = False
    for line in f.readlines():
        if len(line) > 0 and line[0] in integers:
            flag=True
            temp += line
        elif flag :
            days.append(temp)
            temp =''
            flag=False
    if flag:
        days.append(temp)
    return days
            
def format_record(s):    
    s = s.replace(":",".")
    s = s.replace('*','')    
    return s

def format_list(lisT):
    return map(format_record, lisT)

def list_2_string(lisT):
    separator = '", "'
    s = separator.join(lisT)
    return '"' + s + '"'

def write(string, filename):
    f = open(filename, 'w')    
    f.write(string+'\n')
    f.close()

def get_lists(string):
    f = StringIO.StringIO(string)
    reader = get_csv_reader(f)
    a = transpose(reader)
    south, north = split_table(a)
    return south, north

def main(filename):
    f = open(filename, 'r')
    i = 0
    for day in split_days(f):
        if i > 2: break
	south_offset_val = south_off_values.get(filename, 1)
	
        south, north = get_lists(day)
        south_offset = offset_gen(south,south_offset_val)
        north_offset = offset_gen(north)
        #south = format_list(south)
        #north = format_list(north)
        
        dictionary[from_N][day_order[i]][dep] = format_list(north)
        dictionary[from_N][day_order[i]][off] = north_offset
        dictionary[from_S][day_order[i]][dep] = format_list(south)
        dictionary[from_S][day_order[i]][off] = south_offset
        i+=1
        
    write(json.dumps(dictionary, ensure_ascii=False), filename+'.json')
    #
    #a = transpose(reader)
    #one, another = split_table(a)
    #z_one, z_another = zero_str_list(len(one)), zero_str_list(len(another))
    #s = map(list_2_string, [one, z_one, another,  z_another])
    #write(s, filename+'_jsoned.txt')
    #f.close()


if __name__ == '__main__':
    for filename in  sys.argv[1:]:
        main(filename)
