package com.example.lottonumber;

public class LottoNumbers {
    private int[] number;

    public LottoNumbers(int[] lottoNumber) {
        this.number = new int[6];
        this.number[0] = lottoNumber[0];
        this.number[1] = lottoNumber[1];
        this.number[2] = lottoNumber[2];
        this.number[3] = lottoNumber[3];
        this.number[4] = lottoNumber[4];
        this.number[5] = lottoNumber[5];
    }

    public int[] getNumber() {
        for (int i = 1; i < 6; i++) {
            for(int j = 0 ; j < 6 ; j++){
                if(number[i] < number[j]){
                    int temp = number[i];
                    number[i] = number[j];
                    number[j] = temp;
                }
            }
        }
        return number;
    }

    public void setNumber(int[] number) {
        this.number = number;
    }
}
