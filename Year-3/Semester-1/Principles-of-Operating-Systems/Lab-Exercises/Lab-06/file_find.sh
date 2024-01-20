filefind ()
{
        cd $1;
        for i in *
        do
                if [ $i = $2 ]
                then echo `pwd`/$i
                elif [ -d $i ]
                then filefind $i $2
                fi
        done
        cd ..
}
filefind $1 $2

