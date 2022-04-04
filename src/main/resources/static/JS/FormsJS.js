
//Botão Animado

var animateButton = function(e) {

	e.preventDefault;
	e.target.classList.remove('animate');

	e.target.classList.add('animate');
	setTimeout(function() {
		e.target.classList.remove('animate');
	}, 700);
};

var btnBlue = document.getElementsByClassName("confetti-button-blue");
var btnRed = document.getElementsByClassName("confetti-button-red");

for (var i = 0; i < btnBlue.length; i++) {
	btnBlue[i].addEventListener('click', animateButton, false);
}
for (var i = 0; i < btnRed.length; i++) {
	btnRed[i].addEventListener('click', animateButton, false);
}

//Transição Cadastro para Login

const background = document.querySelector("body")
const personagem = document.querySelector(".personagem")
const formCadastro = document.querySelector(".formCadastro")
const formLogin = document.querySelector(".formLoginOff")
const circle = document.querySelector("#circle")

let switchBtn = document.getElementById("switch-id")

switchBtn.addEventListener('click', () => {
	if (document.getElementById("switch-id").checked == true) {
		circle.classList.replace("Off", "On")
		document.getElementById("switch-id").disabled = true
		setTimeout(function() {
			document.getElementById("personagem").src = "../IMG/darkside.png"
			background.classList.replace("background1", "background2")
			formCadastro.classList.replace("formCadastro", "formCadastroOff")
			formLogin.classList.replace("formLoginOff", "formLogin")
		}, 2000);
		setTimeout(function() {
			document.getElementById("switch-id").disabled = false
		}, 3000);
	} else {
		circle.classList.replace("On", "Off")
		document.getElementById("switch-id").disabled = true
		setTimeout(function() {
			document.getElementById("personagem").src = "../IMG/thanos.png"
			background.classList.replace("background2", "background1")
			formCadastro.classList.replace("formCadastroOff", "formCadastro")
			formLogin.classList.replace("formLogin", "formLoginOff")
		}, 2000);
		setTimeout(function() {
			document.getElementById("switch-id").disabled = false
		}, 3000);
	}
})


