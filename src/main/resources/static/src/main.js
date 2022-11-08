/*let navbar=document.getElementById("navbar")
document.addEventListener("scroll",()=>{
    if(document.body.scrollTop>90 ||document.documentElement.scrollTop > 80){
        navbar.classList.add("bg-black");
        navbar.classList.remove("bg-transparent");
    }else{
        navbar.classList.remove("bg-black");
        navbar.classList.add("bg-transparent");
    }
})*/
let login=document.getElementById("login");
let filter=document.getElementById("filter");
let border_productos=document.getElementById("border-productos")
function setPage(){
    if(window.innerWidth<720){
        login.classList.add("d-none")
        filter.classList.add("border-bottom");
        border_productos.classList.remove("border-start");
    }else{
        login.classList.remove("d-none");
        filter.classList.remove("border-bottom");
        border_productos.classList.add("border-start");
    }
}
setPage();
window.addEventListener("resize",setPage);


