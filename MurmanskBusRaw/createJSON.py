# -*- coding: utf-8 -*-
"""
Created on Tue Sep 16 18:03:57 2014

@author: pavel
"""
import sys, json
reload(sys)
sys.setdefaultencoding('utf-8')

st_str = "stations" 
del_str = "delays"
delimiter = ';'

def write(filename, string):
    f = open(filename, 'w')
    
    f.write(string+'\n')
    
    
    f.close()

def get_lists(filename):
    f = open(filename, 'r')
    stations = []
    delays = []
    for line in f.readlines():
        (s, d) = line.split(delimiter)          
        stations.append(s.strip().decode('utf8'))
        delays.append(d.strip())
    f.close()
    return stations, delays

def main(filename):
    print filename    
    stations, delays= get_lists(filename)
    
    s = {st_str:stations, del_str:delays}
    write(filename+'.json',json.dumps(s, ensure_ascii=False))


if __name__ == '__main__':
    for filename in sys.argv[1:]:
        main(filename)
