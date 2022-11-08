let filtro_marca=document.getElementById("form_marca")
let filtro_sexo=document.getElementById("form_sexo")
let filtro_nombre=document.getElementById("form_nombre")
let sin_filtro=document.getElementById("no-filter")
let check_sexo=document.getElementsByClassName("sexo");
let check_marca=document.getElementsByClassName("marca");
function save(key,value){
    localStorage.setItem(key,JSON.stringify(value));
}
sin_filtro.addEventListener("submit",()=>{
    localStorage.clear();
})
for(let check of check_sexo){
    check.addEventListener("change",(e)=>{
        localStorage.clear();

        for(let i=0;i<check_sexo.length;i++){
            if(check_sexo[i].checked && check_sexo[i]!=check){
                check_sexo[i].checked=false;
            }
        }
        if(check.checked==false){
            document.getElementById("filtro_sexo").setAttribute("value","NOMBRE")
        }
        check.checked && save(check.getAttribute("value"),true)
        filtro_sexo.submit();

    })
}
for(let check of check_marca){
    check.addEventListener("change",(e)=>{
        localStorage.clear();
        for(let i=0;i<check_marca.length;i++){
            if(check_marca[i].checked && check_marca[i]!=check){
                check_marca[i].checked=false;
            }
            if(check_marca[i].checked==false && check_marca[i]==check){
                filtro_nombre.submit();
            }
        }
        if(check.checked==false){
            document.getElementById("filtro_marca").setAttribute("value","NOMBRE")
        }
        check.checked && save(check.getAttribute("value"),true)
        filtro_marca.submit();
    })
}
window.onload=(e)=>{
    for(let check of check_sexo) {
        let value=JSON.parse(localStorage.getItem(check.getAttribute("value")))
        if(value==true){
            check.checked=true
            localStorage.removeItem(check.getAttribute("value"))
        }
    }
    for(let check of check_marca) {
        let value=JSON.parse(localStorage.getItem(check.getAttribute("value")))
        if(value==true){
            check.checked=true
            localStorage.removeItem(check.getAttribute("value"))
        }
    }
}
