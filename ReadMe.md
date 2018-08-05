#映射两种方式
 1、 简单方式
 
       @ManyToOne
       @JoinColumn(name = "todo_id")
       @JsonIgnore
       private ToDo toDo;
       
       @OneToMany(cascade = CascadeType.ALL)
           @JoinColumn(name = "todo_id")
           private List<Task> tasks;
           
       TodoService   
       public void create(ToDo todo) {
          toDoRepository.save(todo);
       }    
           
 2、 复杂方式
 
       @ManyToOne
       @JoinColumn(name = "todo_id")
       @JsonIgnore
       private ToDo toDo;
       
       @OneToMany(targetEntity = Task.class , cascade = CascadeType.ALL,mappedBy = "toDo") // 关系被维护端
       @JoinColumn(name = "todo_id")
       private List<Task> tasks;  
       
       
        TodoService
        public void create(ToDo todo) {
          todo.getTasks().forEach(task -> task.setToDo(todo));
          toDoRepository.save(todo);
        }
        
        
 #migration
 1、添加脚本语法错误，运行报错
   删除schema_version中对应的脚本，重新执行
 
 2、脚本语法正确，而且报错原因时表的关联关系导致的，这种情况下。
   手动跑一边
   直接手动更改schema_version中对应的version的success
    
    
 #级联删除
 在Todo上添加直接设置CascadeType.DELETE
 
 #软删除
 在Todo上添加
 @SQLDelete(sql="update todo set deleted=true where id = ?")
 @Where(clause = "deleted = false")  