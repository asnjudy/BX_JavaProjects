import os
import re

list1 = [{'name': '3'}, {'name': '4'}]
list2 = [{'desc': 'test2'}, {'desc': 'test3'}]

for index in range(len(list2)):
    list1[index]['desc'] = list2[index]['desc']

print list1