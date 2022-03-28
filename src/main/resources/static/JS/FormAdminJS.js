var animateButton = function (e) {

    e.preventDefault;
    //reset animation
    e.target.classList.remove('animate');

    e.target.classList.add('animate');
    setTimeout(function () {
        e.target.classList.remove('animate');
    }, 700);
};

var classname = document.getElementsByClassName("confetti-button");

for (var i = 0; i < classname.length; i++) {
    classname[i].addEventListener('click', animateButton, false);
}


//menu Burguer

const menu = document.querySelector(".checkbox4")
const nav = document.querySelector("nav")
let openmenu = false;

menu.addEventListener('click', ()=>{
	if(!openmenu){
		nav.classList.remove('nav')
		nav.classList.add('show')
		openmenu = true
	}else{
		nav.classList.remove('show')
		nav.classList.add('nav')
		openmenu = false
	}
	
})

/*const menu = document.querySelector(".checkbox4")
const nav = document.querySelector(".nav")

var chamarNav = function(e) {
	e.preventDefault;
	e.target.classList.remove("nav")
	e.target.classList.add("show")
}

for (var i = 0; i < menu.length; i++) {
	menu[i].addEventListener('onclick', chamarNav);
}*/


