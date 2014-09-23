# -*- coding: utf-8 -*-
"""
Created on Tue Sep 16 18:03:57 2014

@author: pavel
"""
import sys, json, os.path
reload(sys)
sys.setdefaultencoding('utf-8')

template_file = "data_template.json"
result_file ="data.json"

directions = {"N":"from_north", "S":"from_south"}


txt_ext = ".txt"
stations_str = "stations" 
delays_str = "delays"
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

def main():
    template = open(template_file, 'r')
    j = json.load(template)
    template.close()

    buses =  j["bus_list"]
    for bus in buses:
        for d in directions.keys():
            if os.path.exists(bus+d+txt_ext):
                stations, delays= get_lists(bus+d+txt_ext)
                j["bus_info"][bus][directions[d]][stations_str] = stations
                j["bus_info"][bus][directions[d]][delays_str] = delays
    print j
    #
    
    #s = {st_str:stations, del_str:delays}
    write(result_file,json.dumps(j, ensure_ascii=False, indent=1, sort_keys=True))
    
    

if __name__ == '__main__':    
   main()
