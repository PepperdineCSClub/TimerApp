#include <iostream>
using namespace std;

void print(const int v[], int numItems) {
    for(int i = 0; i < numItems; i++) {
        cout << v[i] << " ";
    }
    cout << endl;
}

// example of recursive sorts w/ Susan Merritt Taxonomy
void selectionSort(int v[], int numItems) {
    if (numItems > 1) {
        //SPLIT
        int maxIdx = 0;
        for (int i = 1; i < numItems; i++) {
            maxIdx = (v[i] > v[maxIdx] ? i : maxIdx);
        }
        int temp = v[maxIdx];
        v[maxIdx] = v[numItems - 1];
        v[numItems - 1] = temp;

        //SORT
        selectionSort(v, numItems - 1);

        //JOIN
        //    no work to do
    }
}

// 0     1  2  3  4  5  6  7
// 30 -- 60 10 50 40 80 20 70

// 30 -- 60 10 50 40 80 20 70
// 30 10 -- 60 50 40 80 20 70
// 30 10 20 -- 60 50 40 80 70 //done with split
// 10 30 20 --                //begin sort
//

void quickSort(int v[], int start, int end) {
    //no base case b/c if start = end, do nothing
    if (start < end) { //recursive case
        // SPLIT
        int temp = v[start];
        v[start] = v[end];
        v[end] = temp;  // swapping first and last in order to prevent infinite recursion (watch lecture 3/15 if ?)

        int key = v[start];
        int edge = start;
        for (int i = start; i <= end; i++) { // edge remains the same while v[i] does not equal v[edge]
            if (v[i] <= key) {
                temp = v[i];
                v[i] = v[edge];
                v[edge++] = temp;
            }
        }
        // SORT
        quickSort(v, start, edge - 1);
        quickSort(v, edge, end);

        // JOIN
        //     no work to do
    }
}

const int CAPACITY = 8;

// 30 60 10 50 / 40 80 20 70
void mergeSort(int v[], int start, int end) {
    if (start < end) {
        // SPLIT
        int mid = (start + end) / 2;

        // SORT
        mergeSort(v, start, mid); //sorting 1st half
        mergeSort(v, mid + 1, end); //sorting 2nd half

        // 10 30 50 60 / 20 40 70 80
        // JOIN
        int w[CAPACITY];
        int i = start, j = mid + 1, k = start;
        while (i <= mid && j <= end) {
            if (v[i] < v[j]) {
                w[k++] = v[i++];
            }
            else {
                w[k++] = v[j++];
            }
        }
        if (i == mid) {
            for (j = j; j <= end; j++) {
                w[k++] = v[j];
            }
        }
        else {
            for (i = i; i <= mid; i++) {
                w[k++] = v[i];
            }
        }
        for (int i = start; i <= end; i++) {
            v[i] = w[i];
        }
    }
}

int main() {
    int x[CAPACITY] = {30, 60, 10, 50, 40, 80, 20, 70};
    print(x, 8);
    mergeSort(x, 0, 7);
    print(x, 8);
    return 0;
}

// ternary operator -   test ? value_if_true : value_if_false