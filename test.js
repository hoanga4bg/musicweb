var arr = [{   
    releaseId: 1,
    relation: [
        {
            releaseId: 2,
            relation: [
                {
                    releaseId: 4,
                    relation: [
                        {
                            releaseId: 6,
                            relation: [
                                
                            ]
                        }
                    ]
                },
                {
                    releaseId: 5,
                    relation: [
                    ]
                }
            ]
        },
        {
            releaseId: 3,
            relation: []
        }
    ]
}
]

let data = {
    releaseId: 5,
    relation: [
        {
            releaseId: 10,
            relation: [
                
            ]
        }
    ]
}

let way = [1];


let i=0;
function replace(recusiveArr, data){
    for(let index = 0; index < recusiveArr.length; index++){
        if(recusiveArr[index].releaseId === way[i]){
            if(i === (way.length - 1)) {
               recusiveArr[index] = data;
               return;
            }
            i++;
            replace(recusiveArr[index].relation, data);
            break;
        }    
    }
 }
replace(arr,data);

console.log(arr);