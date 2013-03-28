require '../../../Utils.rb'

md_list = []

Dir.open(Dir.pwd).each do |file|
	if file.include? "RP"
		md_list << ResultFile.new(file)
	elsif file.include? "PCA"
		md_list << ResultFile.new(file)
	end
end

li = []
md_list.each { |cluster| li << cluster.percent_incorrect}
p li.join(',')


