let img_principal=document.getElementById("img-ppal")
let imgs_secundarias=document.getElementsByClassName("img-second")

for(let img of imgs_secundarias){
    img.getAttribute("src")==img_principal.getAttribute("src") && img.classList.add("border","border-2","border-warning")
    img.addEventListener("click",()=>{
       let ruta=img.getAttribute("src")
       img_principal.setAttribute("src",ruta);
       img.classList.add("border","border-2","border-warning")
       for(let i=0;i<imgs_secundarias.length;i++){
           if(imgs_secundarias[i].getAttribute("src")!=img.getAttribute("src")){
               imgs_secundarias[i].classList.remove("border","border-2","border-warning")
           }
       }
   })
}

let detalleActual=document.getElementById("detalle_actual")
let colores=[{nombre:"Rojo",color:"#E40232"},
    {nombre: "Negro",color: "#000000"},
    {nombre:"Gris",color: "#cfcfcf"},
    {nombre:"Blanco",color: "#ffffff"},
    {nombre:"Azul",color: "#00008b"},
    {nombre:"Verde",color: "#3ba619"},
    {nombre:"Celeste",color:"#01f6ff"},
    {nombre:"Naranja",color: "#ff8700"},
    {nombre:"Bordo",color: "#620a2b"}]
let form_colores=document.getElementsByClassName("colores")
for(let f of form_colores){

    for(let color of colores){
    color.nombre==f.id && (f.style.background=color.color)
    }
    f.id==detalleActual.getAttribute("value")&&(f.classList.add("border","border-2","border-warning"));
    f.addEventListener("click",()=> {
        for(let i=0;i<form_colores.length;i++){
            f.classList.add("border","border-2","border-warning")
            if(form_colores[i].id!=detalleActual.getAttribute("value")){
                form_colores[i].classList.remove("border","border-2","border-warning")
            }
        }
    })


}