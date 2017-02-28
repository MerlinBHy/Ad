import os
import sys
import json
import random
from sets import Set

if __name__ == "__main__":
    ad_input_file = sys.argv[1]
    query_id_output_file = sys.argv[2]
    category_id_output_file = sys.argv[2]
    query_id_output = open(query_id_output_file, "w")
    
