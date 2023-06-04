// naive approch (sliding window)

class Solution {
    public int minKBitFlips(int[] nums, int k) {
        int ans = 0;
        int n = nums.length;
        int flipped = 0;
        
        for(int i=0; i<n; i++){
            if(nums[i] == 0){
                if(i+k > n)return -1;

                int K = k;
                flipped++;
                int j = i;
                while(K > 0){
                    nums[j] ^= 1;
                    j++;
                    K--;
                }
            }
        }
        return flipped;

        
    }
}



// optimized approach

class Solution {
    public int minKBitFlips(int[] nums, int k) {
        int flips = 0;
        int n = nums.length;
        Queue<Integer> fq = new LinkedList<>();
        for(int i=0; i<n; i++){
            if((nums[i] == 0 && fq.size()%2 == 0) || (nums[i] == 1 && fq.size()%2 == 1)){
                flips++;
                if(i+k > n)return -1;
                fq.add(i+k-1);
            }
             // fq.peek() -> first element that was added
            if(fq.size() > 0 && fq.peek() == i){
                fq.remove();
            }
        }
        return flips;
    }
}


// Idea behind the solution
// We approach the solution in a Greedy way, that is as soon as we see a 0 we will flip it to 1 and we will skip if we encounter a 1.

// Let's say we flip A[i]=0 to 1, this means the next K-1 elements A[i+1], A[i+2]....A[i+k-1] will be flipped as well.
// Now lets say after the flip, A[i+4]=0 (K>4). It would mean it was initially 1 and now it needs to be flipped again. So, as we make the second flip at A[i+4]  the elementsA[i+4+1], A[i+4+2]....A[i+4+k-1] will be flipped.
// As we observe, elements A[i+4] to A[i+k-1] will undergo two flips.

// And if an element that was initially 0 undergoes two flips it will need to be flipped again to make it 1. If an element that was initially 1 undergoes one flip it will need to be flipped once more to make it 1. So extending this further,

// if number of flips for 0 is even, we require a flip
// & if number of flips for 1 is odd, we require a flip

// Next, we need to know the number of flips for a given index. To do this we maintain a queue and we keep adding the index where the last element is to be flipped. As we reach that index, we remove the index from the queue.

// Lets say for k=6, and at i=3 we are doing a flip, queue will contain [8]
// Next at i=6 we are doing a flip the queue becomes [8, 11]
// 8 will be popped from the queue when the iteration counter reaches at index 8 and likewise for 12, this means for i=6 to i=8 there will be two flips one which was started at i=3 and another at i=6.
// This indicates, the length of queue will hold the number of flips for any index.

// Algorithm:
// We declare a queue which is the flip queue
// The res variable will hold the number of total flips done at any point
// We iterate from 0 till the length of the Array, and do the following
// 3.1 If we encounter a 0 and the no. of flips is even OR we encounter a 1 and the no. of flips is odd we
// increment the result and mark the end i+k-1 in the queue
// 3.2 If the last index of the flip > n we return -1 because then its not possible to achieve the final result
// as dont have enough values to flip
// 3.3 We remove the head of the queue as we reach the last index of the flip
