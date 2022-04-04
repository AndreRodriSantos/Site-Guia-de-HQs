//Menu Burguer

const menu = document.querySelector(".checkbox4")
const nav = document.querySelector("nav")
let openmenu = false;
const burguer = document.querySelector(".checkbox")

menu.addEventListener('click', () => {
	if (!openmenu) {
		nav.classList.remove('nav')
		nav.classList.add('show')
		openmenu = true
	} else {
		nav.classList.remove('show')
		nav.classList.add('nav')
		openmenu = false
	}

})
function uncheck() {
	document.getElementById("checkbox4").checked = false
}