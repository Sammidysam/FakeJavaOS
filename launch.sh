# keep open boolean
# if true will call read after launch
keepOpen=${1:-false}

# keep open method
keepOpen(){
	for i in {1..2};
	do
		echo ""
	done
	echo "Launch complete!"
	echo "Press enter to close."
	read
}

# launch flags
flags="-textmode"

launch(){
	java -jar bin/FakeJavaOS.jar $flags
	if $keepOpen; then keepOpen; fi
}

ant
if [ $? == 0 ];
then
	echo "Complete!"
	clear

	# launch application
	launch
else
	echo "Error on completion!"

	# keep open if wanted
	if $keepOpen; then keepOpen; fi
fi
