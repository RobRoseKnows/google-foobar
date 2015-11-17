import requests
import json
import ast
import math

BASE_URL = 'https://www.find.foo/api/challenge'
TOKEN = 'TOKEN GOES HERE'


# Gets the first solution
def getFirst():
    s = requests.session()
    token = { 'token' : TOKEN }
    res = s.get(BASE_URL, params=token)
    print(res.json())
    return ast.literal_eval(res.json()["challenge"][54:])
    
# Returns the result of the post request.
def postAnswer(ans):
    s = requests.session()
    print(ans)
    payload = { 'answer' : str(ans), 'token' : TOKEN }
    res = s.post(BASE_URL, data=payload)
    return res.json()

# Removes duplicate values from a list.
def challengeOne(values):
    newArr = []
    for val in values:
        if val not in newArr: 
            newArr.append(val)
    return(newArr)

# Helper function to find the difference between two points
def distance(x1, y1, x2, y2):
    xsqrd = (x2 - x1) ** 2
    ysqrd = (y2 - y1) ** 2
    print(math.sqrt(xsqrd + ysqrd))
    return math.sqrt(xsqrd + ysqrd)
    
# Finds the perimeter of a triangle
def challengeTwo(coords):
    arr = ast.literal_eval(coords)
    x = [arr[0], arr[2], arr[4]]
    y = [arr[1], arr[3], arr[5]]
    perim = distance(x[0], y[0], x[1], y[1])
    perim += distance(x[1], y[1], x[2], y[2])
    perim += distance(x[2], y[2], x[0], y[0])
    perim = math.ceil(perim)
    
    print(arr)
    return perim
    
# Instead of implementing a better algorithm (which would've taken a while) I just started from 1000 and found the solution from there.
def challengeThree(n):
    a = 16602747662452097049541800472897701834948051198384828062358553091918573717701170201065510185595898605104094736918879278462233015981029522997836311232618760539199036765399799926731433239718860373345088375054249
    b = 26863810024485359386146727202142923967616609318986952340123175997617981700247881689338369654483356564191827856161443356312976673642210350324634850410377680367334151172899169723197082763985615764450078474174626
    for i in range(1000, n+1):
        a,b = b,a+b
    return b

def main():
    initial = getFirst()
    chal1 = challengeOne(initial)
    solutionOne = postAnswer(chal1)
    print(solutionOne)
    
    arr2 = solutionOne["challenge"][67:]
    chal2 = challengeTwo(arr2)
    solutionTwo = postAnswer(chal2)
    print(solutionTwo)
    
    arr3 = int(solutionTwo["challenge"][10:14])
    print(arr3)
    chal3 = challengeThree(arr3)
    print(chal3)
    solutionThree = postAnswer(chal3)
    print(solutionThree)
    
main()