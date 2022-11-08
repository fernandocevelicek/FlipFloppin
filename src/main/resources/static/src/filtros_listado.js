let filtro_marca=document.getElementById("form_marca")
let filtro_sexo=document.getElementById("form_sexo")
let filtro_nombre=document.getElementById("form_nombre")
let sin_filtro=document.getElementById("no-filter")
let check_sexo=document.getElementsByClassName("sexo");
let check_marca=document.getElementsByClassName("marca");
function save(key,value){
    localStorage.setItem(key,JSON.stringify(value));
}

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

let btn_filters=document.getElementById("btn_filtros")
let titulo_filtro_sex=document.getElementById("filtro_sex_titulo");
let titulo_filtro_marca=document.getElementById("filtro_marca_titulo")
btn_filters.addEventListener("click",()=>{
    document.getElementById("filtros").classList.toggle("d-none");
    if(document.getElementById("filtros").classList.contains("d-none")){
        document.getElementById("flecha_filtro").classList.remove("bi", "bi-caret-down-fill")
        document.getElementById("flecha_filtro").classList.add("bi" ,"bi-caret-right-fill")
    }else{
        document.getElementById("flecha_filtro").classList.add("bi", "bi-caret-down-fill")
        document.getElementById("flecha_filtro").classList.remove("bi" ,"bi-caret-right-fill")
    }
})
titulo_filtro_sex.addEventListener("click",()=>{
    if(filtro_sexo.classList.contains("d-none")){
        filtro_sexo.classList.remove("d-none")
        document.getElementById("flecha_filtro_s").classList.add("bi", "bi-caret-down-fill")
        document.getElementById("flecha_filtro_s").classList.remove("bi" ,"bi-caret-right-fill")
    }else{
        filtro_sexo.classList.add("d-none")
        document.getElementById("flecha_filtro_s").classList.remove("bi", "bi-caret-down-fill")
        document.getElementById("flecha_filtro_s").classList.add("bi" ,"bi-caret-right-fill")
    }
})
titulo_filtro_marca.addEventListener("click",()=>{
    if(filtro_marca.classList.contains("d-none")){
        filtro_marca.classList.remove("d-none")
        document.getElementById("flecha_filtro_m").classList.add("bi", "bi-caret-down-fill")
        document.getElementById("flecha_filtro_m").classList.remove("bi" ,"bi-caret-right-fill")

    }else{
        filtro_marca.classList.add("d-none")
        document.getElementById("flecha_filtro_m").classList.remove("bi", "bi-caret-down-fill")
        document.getElementById("flecha_filtro_m").classList.add("bi" ,"bi-caret-right-fill")
    }
})