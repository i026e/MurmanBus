# -*- coding: utf-8 -*-
"""
Created on Tue Sep 16 07:29:33 2014

@author: pavel
"""

import urllib2
from bs4 import BeautifulSoup
import re

host = "http://murmanbus.ru"
main_link = "/index.php?option=com_content&view=article&id=12&Itemid=37"

def get_soup(url):
    page = urllib2.urlopen(url).read()

    soup = BeautifulSoup(page)
    soup.prettify()
    return soup

def get_links():
    soup = get_soup(host+main_link)

    links = []
    for anchor in soup.findAll('a', href=True):
        links.append(anchor['href'])
    return links
   
def get_table(soup):    
    return soup.findAll('table')

def get_rows(table):
    return table.findAll('tr')
def get_cells(row):
    return row.findAll('td')

def clear_table(table):
    s = table.replace('\n', '')
    s = s.replace('</tr>', '\n')
    s = s.replace('<td>', '').replace('</td>', ';')
    s = re.sub('\<((tr)|(td)|(\/?strong)|(\/?table)|(\/?col)|(\/?span)|(br)|(\/?tbody))[^\>]*\>','', s)
    return s

def get_title(soup):
    return soup.title
def write(filename, data):
    f = open(filename, 'w')
    f.write(data)
    f.close()

def main():
    links = get_links()
    for link in links:
        if link[:4] != 'http':
            print host+link
            soup = get_soup(host+link)
            iD = re.search(u'[0-9]+.?',repr(get_title(soup)),re.U)
            if iD:
                title = iD.group(0).strip()
                table = repr(get_table(soup)[1].contents[3])
                print title
                write('a'+title+'.txt', clear_table(table))
#print links[-1]
#l = "/index.php?option=com_content&view=article&id=585&Itemid=73"
#a= get_table(host+l)[1]
#a = repr(a.contents[3])

#print a.contents[3].string
#print clear_table(a)

if __name__ == '__main__':
    main()