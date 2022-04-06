| Inactive | L | R | U | D | Decimal |
|--------|---|---|---|---|---------|
| 0/1    | 0 | 0 | 0 | 0 | 0       |
| 0/1    | 0 | 0 | 0 | 1 | 1       |
| 0/1    | 0 | 0 | 1 | 0 | 2       |
| 0/1    | 0 | 0 | 1 | 1 | 3       |
| 0/1    | 0 | 1 | 0 | 0 | 4       |
| 0/1    | 0 | 1 | 0 | 1 | 5       |
| 0/1    | 0 | 1 | 1 | 0 | 6       |
| 0/1    | 0 | 1 | 1 | 1 | 7       |
| 0/1    | 1 | 0 | 0 | 0 | 8       |
| 0/1    | 1 | 0 | 0 | 1 | 9       |
| 0/1    | 1 | 0 | 1 | 0 | 10      |
| 0/1    | 1 | 0 | 1 | 1 | 11      |
| 0/1    | 1 | 1 | 0 | 0 | 12      |
| 0/1    | 1 | 1 | 0 | 1 | 13      |
| 0/1    | 1 | 1 | 1 | 0 | 14      |
| 0/1    | 1 | 1 | 1 | 1 | 15      |

Maze Cell stores walls using the above bits. However, decimal is included as BitSets refer to bit location in their structure and do not present these bytes. It does however, print the decimal value when referred to via its other functions. Cells include a toString which will traverse every bit and build them into a string of 0's and 1's