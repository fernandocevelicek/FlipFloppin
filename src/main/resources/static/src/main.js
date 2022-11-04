let navbar=document.getElementById("navbar")
document.addEventListener("scroll",()=>{
    if(document.body.scrollTop>90 ||document.documentElement.scrollTop > 80){
        navbar.classList.add("bg-black");
        navbar.classList.remove("bg-transparent");
    }else{
        navbar.classList.remove("bg-black");
        navbar.classList.add("bg-transparent");
    }
})