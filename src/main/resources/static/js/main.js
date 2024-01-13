async function mainEntry(){

    let response=await fetch("/api/initBoard")
    let data=await response.json();
    //alert(data);

    debug(JSON.stringify(data.board));
    let board=document.getElementById("game-board");
    let rowTmpl = document.querySelectorAll("#templates>.row")[0];
    for(let y=0;y<data.board.cards.length;y++){
        let row=data.board.cards[y];
        let rowhtml=rowTmpl.cloneNode(true);
        board.appendChild(rowhtml);
        for (let x=0;x<row.length;x++) {
            let card = document.querySelectorAll("#templates>.card")[0].cloneNode(true);
            card.setAttribute("data",y+"_"+x);
            card.onclick=onCardClick;
            card.querySelectorAll(".card-front img")[0].src = data.board.cards[y][x].url;
            let cardValue = card.querySelectorAll(".cardValue")[0];
            cardValue.innerHTML=data.board.cards[y][x].value;
            rowhtml.appendChild(card);
        }
    }

}


async function updateBoard(game) {
    let SWAP_PLAYER="SWAP_PLAYER";

    let board=document.getElementById("game-board");



    var cards=game.board.cards;
    for(let y=0;y<cards.length;y++){
        let row=cards[y];
        for (let x=0;x<row.length;x++) {
            let card = board.querySelectorAll(".card[data='"+y+"_"+x+"']")[0];
            if (cards[y][x]!=null) {
                card.querySelectorAll(".card-front img")[0].src = cards[y][x].url;
                if (cards[y][x].value > 0) {
                    card.querySelectorAll(".cardValue")[0].innerHTML = cards[y][x].value;
                } else {
                    card.querySelectorAll(".cardValue")[0].innerHTML = "";
                }
            }
            else {
                card.querySelectorAll(".card-front img")[0].src ="";
            }
        }


    }


    if (game.actions.length>0){

        for(let x=0;x<game.actions.length;x++){
            let action=game.actions[x];
            if (action===SWAP_PLAYER) {
                window.setTimeout(() => {
                    swapPlayerResponse();
                    alert("SWAP_PLAYER. Player " + ((game.activePlayer==1)?2:1) + " your turn!");
                },2000);

            }
        }

    }

}


async function onCardClick(event){
    let carddata=event.currentTarget.getAttribute("data").split("_");
    let x=parseInt(carddata[1]);
    let y=parseInt(carddata[0]);

    //alert(x+" "+y);
    let response=await fetch("/api/clickCard?x="+x+"&y="+y);
    let data=await response.json();
    await updateBoard(data);
}

async function swapPlayerResponse(){
    let response=await fetch("/api/swapPlayerResponse");
    let data=await response.json();
    await updateBoard(data);
}

function debug(msg){

    document.getElementById("debugger").innerHTML=msg;
}