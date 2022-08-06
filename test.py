
import time
import requests

url = "https%3A%2F%2Fdocuments.junior-entreprises.com%2Fkiwi-public%2Fuser%2F45120%2Fprofile%2Ff24b5ac1-4521-4081-86c1-61d690176529.png"

base_url = "http://localhost:8080"

def test():
    start = time.time()
    r = requests.post(f"{base_url}/_?url={url}")
    end= time.time()
    print(r.text)
    print(f'took {(end - start) * 1000} ms')


test()